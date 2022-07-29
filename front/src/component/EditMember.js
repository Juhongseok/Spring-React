import React, { Component } from 'react'
import ApiService from '../ApiService';

import {TextField, Button, Typography} from '@material-ui/core'

export default class EditMember extends Component {
    constructor(props){
        super(props);

        this.state = {
            memberId:'',
            salary:'',
            message:null
        }
    }

    componentDidMount(){
        this.loadMember();
    }

    loadMember = () => {
        ApiService.fetchMemberById(window.localStorage.getItem('memberId'))
            .then(res => {
                let member = res.data;
                this.setState({
                    memberId:member.memberId,
                    salary:member.salary
                })
            })
            .catch(error => {
                console.log('loadMember() Error', error);
            })
            
            
    }

    onChange = (e) => {
        this.setState({
            [e.target.name] : e.target.value
        })
    }

    updateMember = (e) => {
        e.preventDefault();

        let member = {
            memberId:this.state.memberId,
            salary:this.state.salary
        }

        ApiService.updateMember(member)
            .then(res => {
                this.setState({
                    message : member.Id + '님 정보가 수정되었습니다.'
                })
            })
            .catch(error => {
                console.log('updateMember() Error', error);
            })
    }

  render() {
    return (
      <div>
        <Typography variant='h4' style={style}>Edit Member</Typography>
        <form>
            <TextField type="text" placeholder='input Id' name='memberId'
                fullWidth value={this.state.memberId} onChange={this.onChange}/>
            <TextField type="number" placeholder='input salary' name='salary'
                fullWidth value={this.state.salary} onChange={this.onChange}/>
            <Button variant='contained' color='primary' onClick={this.updateMember}>UPDATE</Button> 
        </form>
      </div>
    )
  }
}

const style={
    display: 'flex',
    justifyContent: 'center'
}