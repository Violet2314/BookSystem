import axios from "../axios"

export const loginin = async(params) => {
    try {
        const response = await axios.get('/login',{params});
        return response.data;
    } catch (error) {
        console.log('login is error',error);
    }
}