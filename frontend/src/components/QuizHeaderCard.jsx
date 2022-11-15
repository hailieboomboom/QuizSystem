import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import "../styles/QuizCardStyle.css"
import { useRecoilState } from 'recoil';
import { textState } from '../recoil/Atoms'

export default function QuizShortAnswerCard(props) {
    // const [text, setText] = useRecoilState(textState);


    return (
        <Card className={"cardContent"} sx={{width: 700}}>
            <CardContent>
                <Typography variant="h3" color="text.secondary" gutterBottom>
                    Quiz: {props.quizName}
                </Typography>
                <Typography className={"quizTitle"} color="text.secondary" gutterBottom>
                    Description:
                </Typography>
                <Typography className={"quizString"} >
                    This is a formal test that you take to show your knowledge or ability in a particular subject, or to obtain a skill.
                </Typography>
                <Typography className={"quizTitle"} color="text.secondary" gutterBottom>
                    Instructions:
                </Typography>
                <Typography className={"quizStringRed"} >
                    Open Book
                </Typography>
            </CardContent>
        </Card>
    );
}
