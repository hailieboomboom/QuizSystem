import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import "../styles/QuizCardStyle.css"
import Grid from "@mui/material/Grid";
import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormGroup from "@mui/material/FormGroup";

export default function QuizMsqCard() {
    const [checked, setChecked] = React.useState([false,false,false,false]);

    const handleChange1 = (event) => {
        setChecked([event.target.checked,checked[1],checked[2],checked[3]]);
    };

    const handleChange2 = (event) => {
        setChecked([checked[0],event.target.checked,checked[2],checked[3]]);
    };

    const handleChange3 = (event) => {
        setChecked([checked[0],checked[1],event.target.checked,checked[3]]);
    };
    const handleChange4 = (event) => {
        setChecked([checked[0],checked[1],checked[2],event.target.checked,]);
    };
    return (
        <Card className={"cardContent"} sx={{minWidth: 500, maxWidth: 725}}>
            <CardContent >
                <Typography className={"questionTitle"} color="text.secondary" gutterBottom>
                    Question:
                </Typography>
                <Typography className={"questionString"} variant="h5" component="div">
                    Insert Question here
                </Typography>
                <FormGroup>
                    <FormControlLabel control={
                        <Checkbox
                            checked={checked[0]}
                            onChange={handleChange1}
                        />
                    } label="Label"/>
                    <FormControlLabel control={
                        <Checkbox
                            checked={checked[1]}
                            onChange={handleChange2}
                        />
                    } label="Label"/>
                    <FormControlLabel control={
                        <Checkbox
                            checked={checked[2]}
                            onChange={handleChange3}
                        />
                    } label="Label"/>
                    <FormControlLabel control={
                        <Checkbox
                            checked={checked[3]}
                            onChange={handleChange4}
                        />
                    } label="Label"/>
                </FormGroup>
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
