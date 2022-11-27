import React, {Component} from 'react';
import '../styles/Registration.css';
class Registration extends Component {

    constructor(props) {
        super(props);

        this.state = {
            hiddenfirst: true,
            passwordfirst: '',
            hiddensecond: true,
            passwordsecnd: '',
        };

        this.handlePasswordChangeFirst = this.handlePasswordChangeFirst.bind(this);
        this.toggleShowFirst = this.toggleShowFirst.bind(this);
        this.handlePasswordChangeSecond = this.handlePasswordChangeSecond.bind(this);
        this.toggleShowSecond = this.toggleShowSecond.bind(this);
    }

    handlePasswordChangeFirst(e) {
        this.setState({ passwordfirst: e.target.value });
    }

    toggleShowFirst() {
        this.setState({ hiddenfirst: !this.state.hiddenfirst });
    }

    handlePasswordChangeSecond(e) {
        this.setState({ passwordsecond: e.target.value });
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
                <input className="nickname" type="text"/>
                <label className="post-lable"> Почта:</label>
                <input className="post" type="text"/>
                <label className="password-lable"> Пароль:</label>
                <input className="password"
                       type={this.state.hiddenfirst ? 'password' : 'text'}
                       value={this.state.passwordfirst}
                       onChange={this.handlePasswordChangeFirst}/>
                <button className="eye-button" onClick={this.toggleShowFirst}>
                    <img className="eye" alt="no foto" src="eye.png"/>
                </button>
                <label className="passworddb-lable"> Повторите пароль:</label>
                <input className="passworddb"
                       type={this.state.hiddensecond ? 'password' : 'text'}
                       value={this.state.passwordsecond}
                       onChange={this.handlePasswordChangeSecond}/>
                <button className="eyedb-button" onClick={this.toggleShowSecond}>
                    <img className="eye" alt="no foto" src="eye.png"/>
                </button>
                <button className="reg-button"> Зарегистрироваться </button>
                <a className="authorization" href='/Authorization' > Уже есть аккаунт</a>
            </div>
        );
    }
}

export default Registration;