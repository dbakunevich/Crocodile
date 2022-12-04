import React, {Component} from 'react';
import '../styles/Authorization.css';

class Authorization extends Component {
    constructor(props) {
        super(props);

        this.state = {
            hidden: true,
            password: '',
        };

        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.toggleShow = this.toggleShow.bind(this);
        this.checklog = this.checklog.bind(this);
    }

    checklog() {
        document.getElementById('email-exists').style.color = "red";
        document.getElementById('incorrect-password').style.color = "red";
    }

    handlePasswordChange(e) {
        this.setState({ password: e.target.value });
    }

    toggleShow() {
        this.setState({ hidden: !this.state.hidden });
    }

    componentDidMount() {
        if (this.props.password) {
            this.setState({ password: this.props.password });
        }
    }

    render() {
        return (
            <div className="autho">
                <label id="hiy"></label>
                <label className="login-lable"> Логин:</label>
                <label className="email-exists" id = 'email-exists'> Аккаунт с такой почтой уже существует</label>
                <input className="login" type="text"/>
                <label className="incorrect-password" id = 'incorrect-password'> Неверный пароль</label>
                <label className="pass-lable"> Пароль:</label>
                <input className="pass"
                       type={this.state.hidden ? 'password' : 'text'}
                       value={this.state.password}
                       onChange={this.handlePasswordChange}></input>
                <button className="eye-button" onClick={this.toggleShow}>
                    <img className="eye" alt="no foto" src="eye.png"/>
                </button>
                <button className="in-button" onClick={this.checklog}> Войти </button>
                <a href='/Recovery' className="forgot-password" > Забыли пароль?</a>
                <label className="no-account"> Нет аккаунта?</label>
                <a className="registration" href='/Registration' > зарегистрироваться</a>
            </div>
        );
    }
}

export default Authorization;