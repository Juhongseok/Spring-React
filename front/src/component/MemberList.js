import { Typography, Table, TableBody, TableCell, TableHead, TableRow, Button} from '@material-ui/core';
import React, { Component } from 'react'
import ApiService from '../ApiService'

export default class MemberList extends Component {
    constructor(props){
        super(props);

        this.state={
            members:[],
            message: null,
        }
    }

    componentDidMount(){
        this.reloadMemberList();
    }

    reloadMemberList = () => {
        ApiService.fetchMembers()
            .then(res=>{
                this.setState({
                    members: res.data.data
                })
            })
            .catch(error => {
                console.log("reloadMemberList() Error!!", error);
            })
    }

    deleteMember = (memberId) => {
        ApiService.deleteMember(memberId)
            .then(res => {
                this.setState({
                    message: 'Member delete'
                });
            })
            .catch(error => {
                console.log("deleteMember() Error!!", error);
            })
    }

    editMember = (memberId) => {
        window.localStorage.setItem("memberId", memberId);
    }
    
    addMember = () => {
        window.localStorage.removeItem("memberId");
    }
    
    render() {
        return (
            <div>
                <Typography variant='h4' style={style}>MemberList</Typography>
                <Button variant='contained' color='primary' onClick={this.addMember}> Add Member</Button> 
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell align='center'>ID</TableCell>
                            <TableCell align='center'>NAME</TableCell>
                            <TableCell align='center'>PASSWORD</TableCell>
                            <TableCell align='center'>AGE</TableCell>
                            <TableCell align='center'>SALARY</TableCell>
                            <TableCell >BUTTON</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.members.map(member => 
                            <TableRow component='th' scope='member' key={member.memberId}>
                                <TableCell align='center'>{member.memberId}</TableCell>
                                <TableCell align='center'>{member.memberName}</TableCell>
                                <TableCell align='center'>{member.password}</TableCell>
                                <TableCell align='center'>{member.age}</TableCell>
                                <TableCell align='center'>{member.salary}</TableCell>
                                <TableCell align='center'>
                                    <Button align='right' onClick={() => this.editMember(member.memberId)}>
                                        update
                                    </Button>
                                    <Button align='right' onClick={() => this.deleteMember(member.memberId)}>
                                        delete
                                    </Button>
                                </TableCell>
                            </TableRow>
                            )
                        }
                    </TableBody>
                </Table>
            </div>
        );
    }
}

const style = {
    display: 'flex',
    justifyContent: 'center'
}