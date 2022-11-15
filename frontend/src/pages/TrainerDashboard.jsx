import * as React from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import {Container} from "@mui/material";
import Typography from "@mui/material/Typography";
import { apis } from '../utils/apis';
import {useState, useEffect} from "react";
import Button from '@mui/material/Button';

export default function TrainerDashboard(){
    const [data, setData] = useState([]);
    const [rowData, setRowdata] = useState([]);
    useEffect(() => {
        return () => {
            apis.getUnauthorizedTrainers()
                .then(res => {

                    setRowdata(res.data)
                    console.log(res.data)
                })
        };
    }, []);

    return (
        <TableContainer component={Paper}>
            <Table aria-label="simple table" stickyHeader>
                <TableHead>
                    <TableRow>

                        <TableCell align="right">Username</TableCell>
                        <TableCell align="right">First Name</TableCell>
                        <TableCell align="right">Last Name</TableCell>
                        <TableCell align="right">Email</TableCell>
                        <TableCell align="right">Role</TableCell>
                        <TableCell align="right">Authorize</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rowData.map((row) => (
                        <TableRow key={row.id}>
                            <TableCell component="th" scope="row">
                                {row.username}
                            </TableCell>

                            <TableCell align="right">{row.firstName}</TableCell>
                            <TableCell align="right">{row.lastName}</TableCell>
                            <TableCell align="right">{row.email}</TableCell>
                            <TableCell align="right">{row.role}</TableCell>
                            <TableCell align="right"><Button
                                variant="outlined">Authorize</Button></TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}