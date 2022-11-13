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
            <div className="drawing-things">
                <img className={"colorImage"} src={"gameField/yellow.png"} />
                <img className={"colorImage"} src={"gameField/red.png"} />
                <img className={"colorImage"} src={"gameField/black.png"} />
                <img className={"colorImage"} src={"gameField/green.png"} />
                <img className={"colorImage"} src={"gameField/blue.png"} />
                <img className={"colorImage"} src={"gameField/brown.png"} />

            </div>
        </div>
    )
}
export default Drawer;