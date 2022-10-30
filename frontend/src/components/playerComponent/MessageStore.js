import React, {Component} from "react";



export default class MessageStore extends Component{

    constructor(props) {
        super(props);
    }


    render() {
        return(
            <div>
                {this.props.MessageHolder.map(el => {
                    return(
                        <div>
                            <div className={"messageLine"}>
                                <img className={"likeImage"} src={"gameField/likeUp.png"} onClick={()=>{console.log("g")}}/>
                                <img className={"likeImage"} src={"gameField/likeDown.png"} onClick={()=>{console.log("g")}}/>
                                <ul className={"textOfMessage"}>{el}</ul>
                            </div>
                        </div>
                    )
                })}
            </div>
        )
    }
}
