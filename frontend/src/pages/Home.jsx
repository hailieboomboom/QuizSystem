import React from 'react'
import {Stack} from "@mui/material";
import Box from "@mui/material/Box";

import '../styles/homepageStyle.css'
import Grid from "@mui/material/Grid";

const Home = () => {
    return (

        <div class={"homeDiv"}>
            <Grid className={"homeGrid"}>
                <Stack
                    direction="row"
                    justifyContent="space-between"
                    alignItems="center"
                    spacing={2}
                >
                    <Box>Item 1</Box>
                    <Box>Item 2</Box>
                    <Box>Item 3</Box>
                </Stack>
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
