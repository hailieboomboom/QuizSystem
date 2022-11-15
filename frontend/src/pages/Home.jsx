import React from 'react'

import '../styles/homepageStyle.css'
import Grid from "@mui/material/Grid";

const Home = () => {
    return (
        <div class={"homeDiv"}>
            <Grid className={"homeGrid"}>
                <Grid item>
                    <div className={"homeCenter"}>
                        <h1 className={"homeTitle"}>Welcome, Human!</h1>
                    </div>
                </Grid>
            </Grid>
        </div>
    )
}

export default Home
