import React from "react";
import '../styles/RoomStyle.css';
import TableComponent from "../components/roomComponent/TableComponent";
import RoomCreateComponent from "../components/roomComponent/RoomCreateComponent";

const RoomStore = () => {
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
export default RoomStore;