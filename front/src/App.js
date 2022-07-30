import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import AddMember from "./component/AddMember.js";
import EditMember from "./component/EditMember.js";
import NavBar from "./component/NavBar.js"
import Container from '@material-ui/core/Container'
import MemberList from './component/MemberList.js';

function App() {
  return (
    <div style={style}>
      <NavBar/>
      <Container>
        <BrowserRouter>
          <Routes>
            <Route exact path='/' element={<MemberList/>}/>
            <Route exact path='/members' element={<MemberList/>}/>
            <Route exact path='/add-member' element={<AddMember/>}/>
            <Route exact path='/edit-member' element={<EditMember/>}/>
          </Routes>
        </BrowserRouter>
      </Container>
    </div>
  );
}

const style = {
  marginTop: '20px'
}
export default App;
