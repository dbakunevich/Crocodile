import React from "react";
import '../../styles/RoomStyle.css';
import DictionarComponent from "./DictionarComponent";

function checkMessage() {
    document.getElementById('number').style.color='red'

}

const RoomCreateComponent = () => {
    return (

        <div>
            <button className="game-creation-button" style={{fontSize: 36, fontWeight: 700}} onClick={checkMessage}>
                Создать комнату
            </button>
            <div className="game-creation-field">
                <input type="checkbox" className="checkbox-room"/>
                <label className="lable-text1"> Без пароля</label>
                <label className="lable-text2"> Свободных мест:</label>
                <label className="lable-text3"> Словари:</label>
                <input className="number-players" type="text" id="number"/>
                <DictionarComponent/>
            </div>
        </div>
    )
}

export default RoomCreateComponent;