// Author: Istiaq

import * as React from 'react';
import "../styles/QuizCardStyle.css"
import FormControlLabel from "@mui/material/FormControlLabel";
import RadioGroup from "@mui/material/RadioGroup";
import Radio from "@mui/material/Radio";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import CardActions from "@mui/material/CardActions";
import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";

export default function QuizMcqCard(props) {
    const [value, setValue] = React.useState();

    const handleChange = (event) => {
        setValue(event.target.value);
    };

    return (
        <Card className={"cardContent"} sx={{width: 700}}>
            <CardContent>
                <Typography className={"questionTitle"} color="text.secondary" gutterBottom>
                    Question:
                </Typography>
                <Typography className={"questionString"} variant="h5" component="div">
                    {props.question}
                </Typography>
                <RadioGroup
                    className={"radioGroup"}
                    value={value}
                    onChange={handleChange}
                >
                    <FormControlLabel value="Option 1" control={<Radio/>} label={props.rightAnswer}/>
                    {props.wrongAnswers.map(answer => {
                        return <FormControlLabel key={answer} value={answer} control={<Radio/>} label={answer}/>;
                    })}

                    {/*<FormControlLabel value="Option 3" control={<Radio/>} label="Option 3"/>*/}
                    {/*<FormControlLabel value="Option 4" control={<Radio/>} label="Option 4"/>*/}
                </RadioGroup>
            </CardContent>
            <CardActions>
                <Grid
                    container
                    direction="row"
                    justifyContent="space-between"
                    alignItems="flex-end"
                >
                    <Grid item/>
                    <Button size="small">Submit</Button>
                </Grid>

            </CardActions>
        </Card>


    );
}
