import '../styles/App.css';
import {Route, BrowserRouter, Routes} from "react-router-dom";
import RoomStore from "./RoomStore";
import About from "./About";
import Home from "./Home";
import Room from "./Room";
import React from "react";

const App = () => {
    return(
        <BrowserRouter>
            <div>
                <Routes>
                    <Route path={'/RoomStore'} element= <RoomStore/> />
                    <Route  path='/About' element= <About/> />
                    <Route  path='/' element= <Home/> />
                    <Route path='/Room' element= <Room/> />
                </Routes>

            </div>
        </BrowserRouter>
    )
}

export default App;