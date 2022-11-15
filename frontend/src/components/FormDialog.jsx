import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import {useState, useEffect} from 'react';
import {apis} from '../utils/apis';
import {getUserId} from '../utils/cookies';

export default function FormDialog(props) {
 
    const [username, setUsername] = useState();
    const [email, setEmail] = useState();
    const [firstName, setFirstName] = useState();
    const [lastName, setLastName] = useState();
    const [password, setPassword] = useState();
    const [open, setOpen] = React.useState(false);

    useEffect(()=>{
        setOpen(props.openNow)
    }, [props.openNow])


    const handleClose = () => {
        setOpen(false);
        props.closeWindow();
    };

    const handleSubmit = () => {
        console.log("update info...")

        switch(props.role) {
            case "Trainer":
                apis.updateTrainerInfo(getUserId(), username, password, email, firstName, lastName).then(
                    res => {
                        console.log(res.data)
                        // props.handleRefresh();
                        setOpen(false);
                        props.closeWindow();
                    }
                ).catch(
                    err => console.log(err)
                )
                break;
            case "Sales": 
                apis.updateSalesInfo(getUserId(), username, password, email, firstName, lastName).then(
                    res => {
                        console.log(res.data);
                        setOpen(false);
                        props.closeWindow();
                    }
                ).catch(
                    err => console.log(err)
                )
                break;
            case "Student(Training)":
            case "Student(Pond)":
            case "Student(Beached)":
                apis.updateStudentInfo(getUserId(), username, password, email, firstName, lastName).then(
                    res => {
                        console.log(res.data);
                        setOpen(false);
                        props.closeWindow();
                    }
                ).catch(
                    err => console.log(err)
                )
                break;
            default:
                console.log("No role is matched!(FormDialog)")
        } 
    }

    return (
    <div>
        <Dialog open={open} onClose={handleClose}>
            <DialogTitle>Update</DialogTitle>
        <DialogContent>
            <TextField
            autoFocus
            margin="dense"
            id="username"
            label="Username"
            type="text"
            fullWidth
            variant="standard"
            onChange={e => setUsername(e.target.value)}
            />
            <TextField
            autoFocus
            margin="dense"
            id="email"
            label="Email"
            type="text"
            fullWidth
            variant="standard"
            onChange={e => setEmail(e.target.value)}
            />
            <TextField
            autoFocus
            margin="dense"
            id="password"
            label="Password"
            type="password"
            fullWidth
            variant="standard"
            onChange={e => setPassword(e.target.value)}
            />
            <TextField
            autoFocus
            margin="dense"
            id="firstName"
            label="First name"
            type="text"
            fullWidth
            variant="standard"
            onChange={e => setFirstName(e.target.value)}
            />
            <TextField
            autoFocus
            margin="dense"
            id="lastName"
            label="Last name"
            type="text"
            fullWidth
            variant="standard"
            onChange={e => setLastName(e.target.value)}
            />
        </DialogContent>
        <DialogActions>
            <Button onClick={handleClose}>Cancel</Button>
            <Button onClick={handleSubmit}>Submit</Button>
        </DialogActions>
        </Dialog>
    </div>
    );
}