import React from 'react'
import {Stack} from "@mui/material";
import Box from "@mui/material/Box";

import '../styles/homepageStyle.css'
import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";
import {Link} from "react-router-dom";

const Home = () => {
    return (

        <div className={"homeDiv"}>
            <Grid className={"homeGrid"}>
                {/* <Stack
                    direction="row"
                    justifyContent="space-between"
                    alignItems="center"
                    spacing={2}
                >
                </Stack> */}
                <Grid item>
                    <div className={"homeCenter"}>
                        <h1 className={"homeTitle"}>Welcome to ExQuizIT</h1>
                        {/*<Button variant="contained" state={{ errorPage: "Sorry! you can't access this." }} as={Link} to="/ErrorPage" >Click Me!</Button>*/}
                    </div>
                </Grid>
            </Grid>
        </div>


    )
}

export default Home
