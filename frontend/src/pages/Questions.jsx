import React from 'react'
import Typography from '@mui/material/Typography';
import ViewMCQCard from "../components/ViewMCQCard";
import '../styles/ViewQuestionsCard.css';
import Grid from "@mui/material/Grid";


const Questions = () => {
    return (


        <Grid
            container
            direction="column"
            justifyContent="space-around"
            alignItems="center"
        >

            <div><h1>Multiple Choice Questions</h1>
            <Typography className="headerTest" >test</Typography>
               <Grid item>
                   <ViewMCQCard/>
               </Grid>
                <Grid item>
                    <ViewMCQCard/>
                </Grid>
                <Grid item>
                    <ViewMCQCard/>
                </Grid>
                <Grid item>
                    <ViewMCQCard/>
                </Grid>

            </div>
        </Grid>


    )


}
export default Questions