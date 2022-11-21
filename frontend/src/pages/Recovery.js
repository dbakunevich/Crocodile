import React, {Component} from 'react';
import '../styles/Recovery.css';
class Recovery extends Component {
    render() {
        return (
            <div className="recovery">
                <label className="info1"> Введите почту, на которую</label>
                <label className="info2"> был зарегистрирован аккаунт:</label>
                <input className="recovery-post" type="text"/>
                <button className="recovery-button"> Восстановить пароль </button>
                <a className="authorization-from-recovery" href='/Authorization' > Вспомнил пароль</a>
            </div>
        );
    }
}

export default Recovery;