import Grid from "@mui/material/Grid";
import {Stack} from "@mui/material";
import Box from "@mui/material/Box";

import { useLocation } from 'react-router-dom'

import React from "react";


const ErrorPage = () => {

    const location = useLocation();
    const { errorPage } = location.state;

    return (
        <div>
            <h1>{errorPage}</h1>
        </div>

    )
}
export default ErrorPage