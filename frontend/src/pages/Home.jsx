import React from 'react'
import {Stack, styled} from "@mui/material";
import Box from "@mui/material/Box";

import '../styles/homepageStyle.css'
import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";
import {Link} from "react-router-dom";
import Typography from "@mui/material/Typography";

const Title = styled(Typography)(({ theme }) => ({
    fontSize: "64px",
    color: "#000336",
    fontWeight: "bold",
    margin: theme.spacing(4, 0, 4, 0),
    [theme.breakpoints.down("sm")]: {
        fontSize: "40px",
    },
}));

const Home = () => {
    return (

        <div className={"homeDiv"}>
            <Grid className={"homeGrid"}>
                <Stack
                    className={"homeCenter"}
                    alignItems="center"
                    spacing={3}
                >

<Box sx={{

    backgroundColor: 'white',opacity: [0.1, 0.7, 1],

}}>
    <Title>
        Welcome to XQuizIT
    </Title>
    <Title>
        Quiz Maker for FDMers
    </Title>
</Box>


                </Stack>

            </Grid>
        </div>


    )
}

export default Home
