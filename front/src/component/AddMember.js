import React, { Component } from 'react'
import ApiService from '../ApiService'

import {TextField, Button, Typography} from '@material-ui/core'

export default class AddMember extends Component {
    constructor(props){
        super(props);

        this.state = {
            memberId:'',
            memberName: '',
            password:'',
            age:'',
            salary:'',
            message:null
        }
    }
    onChange = (e) => {
        this.setState({
            [e.target.name] : e.target.value
        })
    }

    saveMember = (e) => {
        e.preventDefault();

        let member = {
            id:this.state.memberId,
            name: this.state.memberName,
            password:this.state.password,
            age:this.state.age
        }
        ApiService.addMember(member)
            .then(res => {
                if(res.data.message === 'success'){
                    this.setState({
                        message: member.name + '님이 등록되었습니다',
                    })
                    console.log(this.state.message);
                }
            })
            .catch(error => {
                console.log('addMember() Error', error);
            })
    }

  render() {
    return (
      <div>
        <Typography variant='h4' style={style}>Add Member</Typography>
        <form style={form}>
            <TextField type="text" placeholder='input Id' name='memberId'
                fullWidth value={this.state.memberId} onChange={this.onChange}/>
            <TextField type="text" placeholder='input name' name='memberName'
                fullWidth value={this.state.memberName} onChange={this.onChange}/>
            <TextField type="password" placeholder='input password' name='password'
                fullWidth value={this.state.password} onChange={this.onChange}/>
            <TextField type="number" placeholder='input age' name='age'
                fullWidth value={this.state.age} onChange={this.onChange}/>
            <TextField type="number" placeholder='input salary' name='salary'
                fullWidth value={this.state.salary} onChange={this.onChange}/>

            <Button variant='contained' color='primary' onClick={this.saveMember}>SAVE</Button> 
        </form>
      </div>
    )
  }
}

const form={
    display: 'flex',
    flexFlow: 'row wrap'
}

const style = {
    display: 'flex',
    justifyContent: 'center'
}