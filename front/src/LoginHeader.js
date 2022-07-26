import { Component } from "react";

export default class LoginHeader extends Component{
    render(){
        return(
            <div class="container">
                <nav class="navbar navbar-expand-lg">
                    <a class="navbar-brand" href="/">Home</a>
                    <div class="justify-content-end collapse navbar-collapse">
                        <button type="button" class="btn btn-primary">로그아웃</button>
                        <button type="button" class="btn btn-primary">정보보기</button>
                    </div>
                </nav>
            </div>
        )
    };
}