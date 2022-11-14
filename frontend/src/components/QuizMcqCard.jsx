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
import axios from "axios";

export default function QuizMcqCard(props) {


    const handleChange = (event) => {
        setValue(event.target.value);
    };

    React.useEffect(() => {
        axios.get("http://localhost:8088/QuizSystem/api/questions/mcqs/"+props.questionId+"").then((response) => {
            setQuestion(response.data);
            console.log(question)
        }).catch(function (error) {
            console.log(error);
        });
    }, []);

    const [value, setValue] = React.useState("");
    const [question, setQuestion] = React.useState();

    if (!question) return null;
    return (
        <Card className={"cardContent"} sx={{width: 700}}>
            <CardContent>
                <Typography className={"questionTitle"} color="text.secondary" gutterBottom>
                    Question:
                </Typography>
                <Typography className={"questionString"} variant="h5" component="div">
                    {question.questionDetails}
                </Typography>
                <RadioGroup
                    className={"radioGroup"}
                    value={value}
                    onChange={handleChange}
                >
                    {question.options.map(answer => {
                        return <FormControlLabel key={answer.optionDescription} value={answer.optionDescription} control={<Radio/>} label={answer.optionDescription}/>;
                    })}

                    {/*<FormControlLabel value="Option 3" control={<Radio/>} label="Option 3"/>*/}
                    {/*<FormControlLabel value="Option 4" control={<Radio/>} label="Option 4"/>*/}
                </RadioGroup>
            </CardContent>
        </Card>


    );
}
