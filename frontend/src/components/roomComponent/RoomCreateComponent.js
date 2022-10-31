import React from "react";
import '../../styles/RoomStyle.css';
import DictionarComponent from "./DictionarComponent";


const RoomCreateComponent = () => {
    const[message, setMessage] = React.useState('');

    const newMessage = (e) => {
        let a = e.target.value;
        setMessage(a);
        if (message === ''){
            document.getElementById('CountPlayers').style.color = "black";
        }
    }

    const checkMessage = () => {
        if (!isNaN(parseFloat(message)) && !isNaN(message - 0) ){
            document.getElementById('CountPlayers').style.color = "green";
        }
        else {
            document.getElementById('CountPlayers').style.color = "red";
        }
    }


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
                <input className="number-players" type="text" id="CountPlayers" onChange={newMessage}/>
                <DictionarComponent/>
            </div>
        </div>
    )
}

export default RoomCreateComponent;