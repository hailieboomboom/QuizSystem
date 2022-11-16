import Grid from "@mui/material/Grid";
import {Stack} from "@mui/material";
import Box from "@mui/material/Box";

import { useLocation } from 'react-router-dom'

import React from "react";


const ErrorPage = (props) => {

    const location = useLocation();
    const { errorPage } = location.state;

    if(props.errorPage)
    return (
        <div>
            <h1>{props.errorPage}</h1>
        </div>

    )

    return (
        <div>
            <h1>{errorPage}</h1>
        </div>

    )
}
export default ErrorPage
