import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import "../styles/QuizCardStyle.css"
import { useRecoilState } from 'recoil';
import { textState } from '../recoil/Atoms'

export default function QuizShortAnswerCard() {
    // const [text, setText] = useRecoilState(textState);


    return (
        <Card className={"cardContent"} sx={{width: 700}}>
            <CardContent>
                <Typography variant="h3" color="text.secondary" gutterBottom>
                    Quiz:
                </Typography>
                <Typography className={"quizTitle"} color="text.secondary" gutterBottom>
                    Description:
                </Typography>
                <Typography className={"quizString"} >
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum turpis leo, pretium sit amet dignissim in, fermentum quis nunc. Etiam gravida elementum lacus, vel rhoncus lectus laoreet tincidunt.
                </Typography>
                <Typography className={"quizTitle"} color="text.secondary" gutterBottom>
                    Instructions:
                </Typography>
                <Typography className={"quizStringRed"} >
                    Mauris nec blandit diam, eget dapibus nisi. Nunc varius elit nunc, sed efficitur diam tincidunt eu. Donec sed luctus ante, nec luctus sapien. Nulla pulvinar elementum feugiat.
                </Typography>
            </CardContent>
        </Card>
    );
}
