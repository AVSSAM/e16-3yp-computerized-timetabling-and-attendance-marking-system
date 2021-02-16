import React, { Component } from 'react';
import {Route,BrowserRouter as Router,Switch} from "react-router-dom";
import Home from "../pages/home"
import Login from "../pages/login"
import StdReg from '../pages/stdReg'
import AdminPanel from '../pages/adminPanel'
import DeleteAccounts from '../pages/deleteAccounts' 
import LecReg from '../pages/lecReg'
import AdminReg from '../pages/adminReg'
import LecturerDashboard from '../pages/lecturerDashboard'
import '../index.css'
import cssBaseline from '@material-ui/core'

class App extends Component {

    state = {}
  
    render() { 
        return (
            <>
            <Router>
                <Switch>
                    <Route path="/home" component={Home}></Route>
                    <Route path="/stdreg" component={StdReg}></Route>
                    <Route path="/adminpanel" component={AdminPanel}></Route>
                    <Route path="/deleteaccounts" component={DeleteAccounts}></Route>
                    <Route path="/lecreg" component={LecReg}></Route>
                    <Route path="/adminreg" component={AdminReg}></Route>
                    <Route path="/Lecturerdashboard" component={LecturerDashboard}></Route>
                    <Route path="/" component={Login}></Route>
                    
                </Switch>
            </Router>
            <cssBaseline/>
            </>
         );
    }

}
 
export default App; 