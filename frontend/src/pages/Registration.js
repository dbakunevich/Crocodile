import React, {Component} from 'react';
import '../styles/Registration.css';
class Registration extends Component {
    render() {
        return (
            <div className="regist">
                <label className="nickname-lable"> Никнейм:</label>
                <input className="nickname" type="text"/>
                <label className="post-lable"> Почта:</label>
                <input className="post" type="text"/>
                <label className="password-lable"> Пароль:</label>
                <input className="password" type="text"/>
                <label className="passworddb-lable"> Повторите пароль:</label>
                <input className="passworddb" type="text"/>
                <button className="reg-button"> Зарегистрироваться </button>
                <a className="authorization" href='/Authorization' > Уже есть аккаунт</a>
            </div>
        );
    }
}

export default Registration;