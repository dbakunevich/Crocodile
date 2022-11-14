import React, {useState} from "react";
import CommunityPart from "./CommunityPart";
import Canvas from "./Canvas"
import "../../styles/Player.css"


const Drawer = () => {

    const [color, setColor] = useState("black");
    const [width, setWidth] = useState(5);

    const changeColor = (color) => {
        setColor(color);
    }
    const changeWidth = (width) => {
        setWidth(width);
    }

    const chC = (e) => {
        changeColor(e.target.value);
    }

    const chW = (e) => {
        console.log(e.target.value);
        changeWidth(e.target.value);
    }



    return (
        <div className="parent-container">
            <div className="draw-board" >
                <Canvas
                    CanWidth={1200}
                    CanHeight={720}
                    color={color}
                    width={width}
                />
            </div>
            <div className="communication-part">
                <div className="settings">settings</div>
                <CommunityPart/>
            </div>
            <div className="drawing-things">
                <img className={"colorImage"} src={"gameField/yellow.png"} onClick={()=>{changeColor("yellow")}} alt={"no"} />
                <img className={"colorImage"} src={"gameField/red.png"} onClick={()=>{changeColor("red")}} alt={"no"}/>
                <img className={"colorImage"} src={"gameField/black.png"} onClick={()=>{changeColor("black")}} alt={"no"}/>
                <img className={"colorImage"} src={"gameField/green.png"} onClick={()=>{changeColor("green")}}  alt={"no"}/>
                <img className={"colorImage"} src={"gameField/blue.png"} onClick={()=>{changeColor("blue")}} alt={"no"}/>
                <img className={"colorImage"} src={"gameField/brown.png"} onClick={()=>{changeColor("brown")}} alt={"no"}/>
                <img className={"colorImage"} src={"gameField/rubber.png"} onClick={()=>{changeColor("white");}} alt={"no"}/>
                <input type="color"  onChange={chC}></input>
                <input type="range" min={1} max={20} onChange={chW}></input>
            </div>
        </div>
    )
}
export default Drawer;