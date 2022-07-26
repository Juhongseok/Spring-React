import DefaultHeader from './DefaultHeader.js';
import LoginHeader from './LoginHeader.js';
import './App.css';
import React from 'react';

function App() {
  var [login, setLogin] = React.useState(false);

  if(login){
    return (
      <div className="App">
          <LoginHeader/>
      </div>
    );
  }else{
    return (
      <div className="App">
          <DefaultHeader/>
      </div>
    );
  }
  
}

export default App;
