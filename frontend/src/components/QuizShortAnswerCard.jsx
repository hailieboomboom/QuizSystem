import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import "../styles/QuizCardStyle.css"
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import { useRecoilState } from 'recoil';
import { textState } from '../recoil/Atoms'

export default function QuizShortAnswerCard() {

    // const [text, setText] = useRecoilState(textState);
    // const onChange = (event) => {
    //     setText(event.target.value);
    // };

    return (
        <Card className={"cardContent"} sx={{width: 700}}>
            <CardContent>
                <Typography className={"questionTitle"} color="text.secondary" gutterBottom>
                    Question:
                </Typography>
                <Typography className={"questionString"} variant="h5" component="div">
                    Insert Question here
                </Typography>
                <TextField  fullWidth id="standard-basic" label="Answer" variant="standard"/>
            </CardContent>
        </Card>


    );
}
