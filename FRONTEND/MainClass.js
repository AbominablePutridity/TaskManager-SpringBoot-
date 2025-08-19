class MainClass {
  apiPatch = "http://localhost:8080/api/";
  
  getData(route, type, login, password) {
    // 4. Используем this для доступа к свойству класса
    const url = this.apiPatch + route;
    
    // 5. Используем переданные login и password, а не строку 'login:password'
    const authHeader = 'Basic ' + btoa(login + ':' + password);
    
    return $.ajax({
      url: url,
      type: type,
      headers: {
        'Authorization': authHeader
      },
      success: function(data) {
        console.log('Успех!', data);

      },
      error: function(xhr) {
        console.log('Ошибка!', xhr.status);

      }
    });
  }
}
