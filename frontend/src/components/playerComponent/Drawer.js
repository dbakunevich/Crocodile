import React from "react";
import CommunityPart from "./CommunityPart";
import "../../styles/Player.css"

const Drawer = () => {
    return (
        <div className="parent-container">
            <div className="draw-board" >drawing field</div>
            <div className="communication-part">
                <div className="settings">settings</div>
                <CommunityPart/>
            </div>
            <div className="drawing-things">drawing things</div>
        </div>
    )
}
export default Drawer;