import React from "react";
import './RoomStyle.css';
import DictionarComponent from "./DictionarComponent";
export default function RoomCreateComponent() {
    return(
        <div>
            <div className="game-creation-field">
                <input type="checkbox" className="checkbox-room"/>
                <label className="lable-text" style={{top: 40, left: 90}}> Без пароля</label>
                <label className="lable-text" style={{top: 92}}> Свободных мест:</label>
                <label className="lable-text" style={{top: 144}}> Словари:</label>
                <DictionarComponent/>
            </div>
        </div>
    )
}