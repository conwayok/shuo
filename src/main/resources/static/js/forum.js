function addClickablePostHeader(postDiv, postData) {
  let cardHeaderDiv = postDiv.childNodes[0];
  cardHeaderDiv.style.cursor = 'pointer';
  cardHeaderDiv.addEventListener('click', function () {
    loadSinglePost(postData.postId);
  });
}

function createPostElement(postData) {
  let authorAndTime = document.createElement('p');
  authorAndTime.style.fontSize = 'small';
  authorAndTime.innerText = `posted by ${postData.author} on ${postData.createTime}`;

  let title = document.createElement('b');
  title.innerText = postData.title;

  let metadata = document.createElement('p');
  metadata.innerText = `${postData.points} points, ${postData.commentCount} comments, ${postData.views} views`;

  let cardHeaderDiv = document.createElement('div');
  cardHeaderDiv.classList.add('card-header');
  cardHeaderDiv.append(authorAndTime, title, metadata);

  let cardBodyDiv = document.createElement('div');
  cardBodyDiv.classList.add('card-body');
  let text = document.createElement('pre');
  text.classList.add('card-text');
  text.style.whiteSpace = 'pre-line';
  text.innerHTML = postData.textContent;

  cardBodyDiv.appendChild(text);

  let cardDiv = document.createElement('div');
  cardDiv.classList.add('card');
  cardDiv.append(cardHeaderDiv, cardBodyDiv);

  return cardDiv;
}

function createCommentElement(commentData) {
  let commentElement = document.createElement('p');
  commentElement.innerText = `${commentData.createTime} | ${commentData.author}: ${commentData.textContent}`;
  return commentElement;
}

function searchKeyword() {

  let keyword = document.getElementById('search').value;

  let path = '/v1/shuo/posts/search?k=' + keyword;

  fetch(path, {
    method: 'get',
    headers: {'Content-Type': 'application/json'}
  })
  .then(response => response.json())
  .then(data => {
    console.log(data);
    let postsArea = document.getElementById('posts-area');
    postsArea.innerHTML = '';
    if (data.payload.length > 0) {
      for (let i = 0; i < data.payload.length; ++i) {
        postsArea.appendChild(createPostElement(data.payload[i]));
        postsArea.appendChild(document.createElement('br'));
      }
    } else {
      postsArea.innerHTML = `<p>no posts found containing \"${keyword}\"</p>`;
    }
  });
}

function loadSinglePost(postId) {
  console.log(postId);

  // get post data
  fetch(`/v1/shuo/posts/${postId}`, {
    method: 'get',
    dataType: 'json',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }
  }).then(response => response.json())
  .then(data => {
    console.log(data);
    let postsArea = document.getElementById('posts-area');
    postsArea.innerHTML = '';
    let postData = data.payload;

    let postElement = createPostElement(postData);

    let commentButtonDiv = document.createElement('div');
    commentButtonDiv.classList.add('card-footer');

    let commentInput = document.createElement('input');
    commentInput.type = 'text';
    commentInput.id = 'commentInput';

    let commentButton = document.createElement('button');
    commentButton.classList.add('btn', 'btn-info', 'mx-2');

    if (!isLoggedIn()) {
      commentButton.disabled = true;
      commentButton.classList.add('disabled');
      commentButton.innerText = 'Login or Register to reply';
    } else {
      commentButton.innerText = 'Reply';
      commentButton.addEventListener('click', function () {
        createComment(postId);
      });
    }

    commentButtonDiv.appendChild(commentInput);
    commentButtonDiv.appendChild(commentButton);

    postElement.appendChild(commentButtonDiv);

    postsArea.appendChild(postElement);
    postsArea.appendChild(document.createElement('br'));

    getComments(postId);
  });

}

function getComments(postId) {
  let postsArea = document.getElementById('posts-area');

  fetch(
      `/v1/shuo/posts/${postId}/comments?page=0&size=999&sort=createTime,desc`,
      {
        method: 'get',
        dataType: 'json',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      })
  .then(response => response.json())
  .then(data => {
    let comments = data.payload;

    if (comments.length > 0) {
      for (let i = 0; i < comments.length; ++i) {
        postsArea.appendChild(createCommentElement(comments[i]));
      }
    } else {
      let p = document.createElement('p');
      p.innerText = 'no comments yet';
      postsArea.appendChild(p);
    }
  });
}

function login() {
  let username = document.getElementById('username').value;
  let password = document.getElementById('password').value;

  let data = {
    username: username,
    password: password
  };

  console.log(JSON.stringify(data));

  fetch('/v1/shuo/authenticate', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(data)
  })
  .then(response => response.json())
  .then(data => {

    console.log(data);

    if (data.hasOwnProperty('token')) {
      alert('success');
      window.sessionStorage.setItem('jwt', data.token);
      window.sessionStorage.setItem('username', username);
      loginSuccess(username);
    } else {
      alert('wrong');
    }
  });
}

function toggleLoginForm() {
  let loginForm = document.getElementById('login-form');
  if (loginForm.style.display === 'none') {
    document.getElementById('register-form').style.display = 'none';
    loginForm.style.display = 'block';
  } else {
    loginForm.style.display = 'none';
  }
}

function logout() {
  window.sessionStorage.clear();
  alert('logged out');
  location.reload();
}

function loginSuccess() {

  document.getElementById('login-form').style.display = 'none';

  // add greeting
  document.getElementById(
      'greetings-item').innerHTML = `<span class="navbar-text"><b>Hello, ${window.sessionStorage.getItem(
      'username')}</b></span>`;

  // disable login and register
  let loginButton = document.getElementById('login-button');
  loginButton.disabled = true;
  loginButton.classList.add('disabled');
  let registerButton = document.getElementById('register-button');
  registerButton.disabled = true;
  registerButton.classList.add('disabled');

  // add logout button
  let logoutButton = document.createElement('button');
  logoutButton.classList.add('btn', 'btn-light');
  logoutButton.innerText = 'Logout';
  logoutButton.addEventListener('click', logout);

  let logoutItem = document.getElementById('logout-item');
  logoutItem.innerHTML = '';
  logoutItem.appendChild(logoutButton);

  document.getElementById('createPostButton').style.display = 'block';
}

function register() {

  let username = document.getElementById('r-username').value;
  let password = document.getElementById('r-password').value;
  let email = document.getElementById('email').value;

  let data = {
    username: username,
    password: password,
    email: email
  };

  console.log(JSON.stringify(data));

  fetch('/v1/shuo/users', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(data)
  })
  .then(response => {
    console.log(response.status);
    if (response.status === 200) {
      alert('Register success! Please login');
      document.getElementById('register-form').style.display = 'none';
      document.getElementById('login-form').style.display = 'block';
      // window.location.replace('/login.html');
    } else if (response.status === 409) {
      alert('username already exists');
    } else {
      window.location.replace('/error');
    }
  });
}

function toggleRegisterForm() {
  let registerForm = document.getElementById('register-form');
  if (registerForm.style.display === 'none') {
    document.getElementById('login-form').style.display = 'none';
    registerForm.style.display = 'block';
  } else {
    registerForm.style.display = 'none';
  }
}

function loadPage() {
  // if logged in
  if (isLoggedIn()) {
    loginSuccess();
  }

  fetch('/v1/shuo/posts?page=0&size=999&sort=createTime,desc', {
    method: 'get',
    dataType: 'json',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }
  })
  .then(response => response.json())
  .then(data => {
    console.log(data);
    let postsArea = document.getElementById('posts-area');
    postsArea.innerHTML = '';
    for (let i = 0; i < data.payload.length; ++i) {
      let postData = data.payload[i];
      let postElement = createPostElement(postData);
      addClickablePostHeader(postElement, postData);
      postsArea.appendChild(postElement);
      postsArea.appendChild(document.createElement('br'));
    }
  });
}

function isLoggedIn() {
  return window.sessionStorage.getItem('jwt') != null
      && window.sessionStorage.getItem('username') != null;
}

function getJwtToken() {
  return sessionStorage.getItem('jwt');
}

function createPost() {
  let title = document.getElementById('postTitle').value;
  let textContent = document.getElementById('postTextContent').value;

  let data = {
    title: title,
    textContent: textContent
  };

  fetch('/v1/shuo/posts', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${getJwtToken()}`
    },
    body: JSON.stringify(data)
  })
  .then(response => {
    if (response.status === 200) {
      alert('success');
    }
    return response.json();
  })
  .then(data => {
    console.log(data.payload.postId);
    loadPage();
  });

}

function createComment(postId) {

  let data = {
    textContent: document.getElementById('commentInput').value
  };

  fetch(`/v1/shuo/posts/${postId}/comments`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${getJwtToken()}`
    },
    body: JSON.stringify(data)
  })
  .then(response => {
        if (response.status === 200) {
          loadSinglePost(postId);
        }
      }
  );
}

loadPage();