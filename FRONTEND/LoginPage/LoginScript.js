// Ждем загрузки страницы
$(document).ready(function() {

  loginField = document.getElementById("loginField");
  passwordField = document.getElementById("passwordField");
  buttonElement = document.getElementById("enterButton");

  result = document.getElementById("result");

  //Обработчик нажатия на кнопку
  buttonElement.onclick = function() {
    //buttonElement.textContent = 'Кнопка была нажата!';

    mainClass = new MainClass();
    
    
    
    mainClass.getData('user/user', 'GET', loginField.value, passwordField.value)
    .then(function(data) {
        console.log('Данные в LoginScript Успешно получены:', data);
        // Работа с данными

        // Извлекаем массив по ключу 'content'
        const usersArray = data.content;

        renderUsers(usersArray);
      })
      .catch(function(error) {
        console.error('Ошибка:', error);
      });
    }

    function renderUsers(users) {
      result.innerHTML = ''; // Очищаем контейнер
    
      users.forEach(user => {
          const userElement = document.createElement('div');
          
          userElement.className = 'user-card';
          userElement.innerHTML = `
            <h3>${user.firstName}</h3>
            <h3>${user.lastName}</h3>
            <p>Дата рождения: ${user.birthDate}</p>
          `;
          
          result.appendChild(userElement);
        });
    }
});
