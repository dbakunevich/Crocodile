import React, {Component} from 'react';
import '../styles/Authorization.css';
class Authorization extends Component {
    render() {
        return (
            <div className="autho">
                <label className="login-lable"> Логин:</label>
                <input className="login" type="text"/>
                <label className="pass-lable"> Пароль:</label>
                <input className="pass" type="text"/>
                <button className="in-button"> Войти </button>
                <a href='/Recovery' className="forgot-password" > Забыли пароль?</a>
                <label className="no-account"> Нет аккаунта?</label>
                <a className="registration" href='/Registration' > зарегистрироваться</a>
            </div>
        );
    }
}

export default Authorization;