import React from "react";
import "../../styles/Player.css"

const Drawer = () => {
    return (
        <div className="parent-container">
            <div className="draw-board" >drawing field</div>
            <div className="communication-part">
                <div className="settings">settings</div>
                <div className="chat">
                    <div className="text-field">text field</div>
                    <div className="enter-button">enter button</div>
                </div>
            </div>
            <div className="drawing-things">drawing things</div>
        </div>
    )
}
export default Drawer;