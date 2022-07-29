import axios from "axios";

const BASE_URL = "http://localhost:8080/";

class ApiService {
    fetchMembers() {
        return axios.get(BASE_URL + 'members');
    }

    fetchMemberById(memberId){
        return axios.get(BASE_URL + 'member/' + memberId);
    }

    deleteMember(memberId){
        return axios.delete(BASE_URL + 'member/' + memberId);
    }

    addMember(member){
        return axios.post(BASE_URL + 'member', member);
    }

    updateMember(member){
        return axios.patch(BASE_URL + 'member/' + member.memberId, member);
    }
}


export default new ApiService();