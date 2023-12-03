// 引入Axios
import service from '../api';

// 设置请求头为 'application/json'
service.defaults.headers.post['Content-Type'] = 'application/json';

// 设置请求的时间，超过这个时间请求中断
service.defaults.timeout = 10000;

service.defaults.withCredentials = true;

// FIXME: 记录学期开始的日期，用于计算当前周数，等待修改成全局变量
const startYear = '2023'
const startMonth = '8'
const startDay = '28'


// 课表类型
export type CourseType = {
    classSession: number,
    courseCredit: number,
    courseDay: number,
    courseEndTime: string,
    courseID: string,
    courseName: string,
    courseNum: number,
    courseNums: number,
    coursePlace: string,
    coursePlan: string,
    courseRoom: string,
    courseStartTime: string,
    courseTeacher: string,
    courseType: string,
    courseWeek: string,
    recordID: number,
    studentID: string,
}

function todayLesson(week: number, day: number, schedule: CourseType[]): CourseType[] {
    const todayLessons: CourseType[] = [];
    for (const lesson of schedule) {
        if (lesson.courseType !== 'special' && lesson.courseDay === day && lesson.courseWeek[week] === '1') {
            todayLessons.push(lesson);
        }
    }
    // 按时间排序
    todayLessons.sort((a, b) => parseInt(a.courseStartTime) - parseInt(b.courseStartTime));
    return todayLessons;
}

function time2day(startTime: Date, time: Date): number[] {
    const daysDifference = Math.floor((time.getTime() - startTime.getTime()) / (1000 * 3600 * 24));
    const week = Math.floor(daysDifference / 7);
    const day = (daysDifference % 7) + 1;
    return [week, day];
}

export const getDayLesson = async (): Promise<CourseType[]> => {
    // 获取当前时间
    const now = new Date()
    // 把 年-月-日 转换成 Date 对象
    const startTime = new Date(`${startYear}-${startMonth}-${startDay}`)
    // 获取当前周数和星期几
    const [week, day] = time2day(startTime, now)
    // 获取课表
    try {
        const schedule = await getCourse()
        // 获取今天的课程
        const todayLessons = todayLesson(week, day, schedule)
        return todayLessons
    } catch (err) {
        console.log(err)
        // 在错误的情况下返回空数组
        return []
    }
}
// 获取课程表
export const getCourse = async () => {
    // 创建一个FormData对象来包含表单数据
    const data: CourseType[] = (await service.post('/urp/user/schedule')).data.data // 直接传递JSON数据对象
    return data
}

