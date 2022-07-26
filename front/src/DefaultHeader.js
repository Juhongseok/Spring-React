import { Component } from "react";

export default class DefaultHeader extends Component{
    render(){
        return(
            <div class="container">
                <nav class="navbar navbar-expand-lg">
                    <a class="navbar-brand" href="/">Home</a>
                    <div class="justify-content-end collapse navbar-collapse">
                        <button type="button" class="btn btn-primary">로그인</button>
                        <button type="button" class="btn btn-primary">회원가입</button>
                    </div>
                </nav>
            </div>
        )
    };
}