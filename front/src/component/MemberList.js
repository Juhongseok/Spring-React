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
                    members: res.data
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
                            <TableCell align='right'>Id</TableCell>
                            <TableCell align='right'>NAME</TableCell>
                            <TableCell align='right'>PASSWORD</TableCell>
                            <TableCell align='right'>AGE</TableCell>
                            <TableCell align='right'>SALARY</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.members.map(member => 
                            <TableRow component='th' scope='member' key={member.memberId}>
                                <TableCell align='right'>{member.memberId}</TableCell>
                                <TableCell align='right'>{member.memberName}</TableCell>
                                <TableCell align='right'>{member.password}</TableCell>
                                <TableCell align='right'>{member.age}</TableCell>
                                <TableCell align='right'>{member.salary}</TableCell>
                                <TableCell align='right'>
                                    <TableCell align='right' onClick={() => this.editMember(member.memberId)}>
                                        create
                                    </TableCell>
                                    <TableCell align='right' onClick={() => this.deleteMember(member.memberId)}>
                                        delete
                                    </TableCell>
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