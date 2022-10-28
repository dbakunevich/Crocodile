import React from "react";
import '../../styles/RoomStyle.css';
import DictionarComponent from "./DictionarComponent";
export default function RoomCreateComponent() {
    return(
        <div>
            <div className="game-creation-field">

                <input type="checkbox" className="checkbox-room"/>
                <label className="lable-text1"> Без пароля</label>
                <label className="lable-text2"> Свободных мест:</label>
                <label className="lable-text3"> Словари:</label>
                <textarea className="number-players"> </textarea>
                <DictionarComponent/>
            </div>
        </div>
    )
}