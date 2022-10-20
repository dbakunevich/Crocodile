import React from "react";
import './RoomStyle.css';

export default function Room() {
    return(
        <div>
            <img className="imgw" src="crocodileWelcoming.png" />
            <button className="game-creation-button" style={{fontSize: 36, fontWeight: 700}}>
                Создать комнату
            </button>
            <div className="game-creation-field">
            </div>
            <img className="imgm" src="crocodileMarsh.png" />
            <div className="table"></div>
        </div>
    )
}