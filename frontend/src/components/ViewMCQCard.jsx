import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';

import '../styles/ViewQuestionsCard.css';

const bull = (
    <Box
        component="span"
        sx={{ display: 'inline-block', mx: '2px', transform: 'scale(0.8)' }}
    >
        •
    </Box>
);

export default function ViewMCQCard() {
    return (
        <div className={"card"}>
            <Card className={"questionCard"} sx={{ minWidth: 275, maxWidth: 500}}>
                <CardContent>
                    <div className={"questionCardContent"}>
                        <div>
                            <Typography className={"questionTitle"} color="text.secondary" gutterBottom>
                                Question:
                            </Typography>
                            <Typography className={"questionString"} variant="h5" component="div">
                                Which is the correct implementation class of BeanFactory?
                            </Typography>
                        </div>
                        <div className={"answerSection"}>
                            <button>XmlBeanFactory</button>
                            <div className="divider"/>
                            <button>ClassPathBeanFactory</button>
                            <div className="divider"/>
                            <button>FileSystemBeanFactory</button>
                            <div className="divider"/>
                            <button>AdvancedBeanFactory</button>
                        </div>
                    </div>


                </CardContent>
                <CardActions>
                    <Button size="small">Edit</Button>
                    <Button size="small">Show Answer</Button>
                </CardActions>
            </Card>
        </div>

    );
}
