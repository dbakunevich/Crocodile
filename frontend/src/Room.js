import React from "react";
import './RoomStyle.css';

export default function Room() {
    return(
        <div>
            <img className="imgs" src="crocodileWelcoming.png" />
            <button className="game-creation-field" style={{fontSize: 36, fontWeight: 700}}>
                Создать комнату
            </button>
        </div>
    )
}