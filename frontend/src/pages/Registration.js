import React, {Component} from 'react';
import '../styles/Registration.css';
class Registration extends Component {

    constructor(props) {
        super(props);

        this.state = {
            hiddenfirst: true,
            passwordfirst: '',
            hiddensecond: true,

            passwordsecond: '',
        };

        this.handlePasswordChangeFirst = this.handlePasswordChangeFirst.bind(this);
        this.toggleShowFirst = this.toggleShowFirst.bind(this);
        this.handlePasswordChangeSecond = this.handlePasswordChangeSecond.bind(this);
        this.toggleShowSecond = this.toggleShowSecond.bind(this);
        this.checkreg = this.checkreg.bind(this);
    }

    checkreg() {
        document.getElementById('email-occupied').style.color = "#FFFFFF";
        document.getElementById('name-occupied').style.color = "#FFFFFF";
        document.getElementById('not-match').style.color = "#FFFFFF";
        document.getElementById('password-short').style.color = "#FFFFFF";
        if (this.state.passwordfirst !== this.state.passwordsecond) {
            document.getElementById('not-match').style.color = "red";
        }
        if (this.state.passwordfirst.length < 5){
            document.getElementById('password-short').style.color = "red";
        }
        document.getElementById('email-occupied').style.color = "red";
        document.getElementById('name-occupied').style.color = "red";
    }

    handlePasswordChangeFirst(e) {
        this.setState({ passwordfirst: e.target.value });
    }

    toggleShowFirst() {
        this.setState({ hiddenfirst: !this.state.hiddenfirst });
    }

    handlePasswordChangeSecond(c) {
        this.setState({ passwordsecond: c.target.value });
    }

    toggleShowSecond() {
        this.setState({ hiddensecond: !this.state.hiddensecond });
    }

    componentDidMount() {
        if (this.props.passwordfirst) {
            this.setState({ passwordfirst: this.props.passwordfirst });
        }
        if (this.props.passwordsecond) {
            this.setState({ passwordsecond: this.props.passwordsecond });
        }
    }

    render() {
        return (
            <div className="regist">
                <label className="nickname-lable"> Никнейм:</label>
                <label className="name-occupied" id = 'name-occupied'>Данное имя уже занято</label>
                <input className="nickname" type="text"/>
                <label className="post-lable"> Почта:</label>
                <label className="email-occupied" id = 'email-occupied'>К данной почте уже привязанаккаунт </label>
                <input className="post" type="text"/>
                <label className="password-lable"> Пароль:</label>
                <label className="password-short" id = 'password-short'>Пароль слишком короткий - минимум 5 символов</label>
                <input className="password"
                       type={this.state.hiddenfirst ? 'password' : 'text'}
                       value={this.state.passwordfirst}
                       onChange={this.handlePasswordChangeFirst}/>
                <button className="eye-button" onClick={this.toggleShowFirst}>
                    <img className="eye" alt="no foto" src="eye.png"/>
                </button>
                <label className="passworddb-lable"> Повторите пароль:</label>
                <label className="not-match" id = 'not-match'> Пароли несовпадают</label>
                <input className="passworddb" id = 'passworddb'
                       type={this.state.hiddensecond ? 'password' : 'text'}
                       value={this.state.passwordsecond}
                       onChange={this.handlePasswordChangeSecond}/>
                <button className="eyedb-button" onClick={this.toggleShowSecond}>
                    <img className="eye" alt="no foto" src="eye.png"/>
                </button>
                <button className="reg-button" onClick={this.checkreg}> Зарегистрироваться </button>
                <a className="authorization" href='/Authorization' > Уже есть аккаунт</a>
            </div>
        );
    }
}

export default Registration;