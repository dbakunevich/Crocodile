import React from "react";
import './style/RoomStyle.css';
import TableComponent from "./roomComponent/TableComponent";
import RoomCreateComponent from "./roomComponent/RoomCreateComponent";

export default function Room() {
    return(
        <div>
            <img className="imgw" src="crocodileWelcoming.png" />
            <img className="imgm" src="crocodileMarsh.png" />
            <button className="game-creation-button" style={{fontSize: 36, fontWeight: 700}}>
                Создать комнату
            </button>
            <RoomCreateComponent/>

            <TableComponent/>
        </div>
    )
}