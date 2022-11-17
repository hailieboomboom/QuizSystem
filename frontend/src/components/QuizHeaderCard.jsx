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

            </CardContent>
        </Card>
    );
}
