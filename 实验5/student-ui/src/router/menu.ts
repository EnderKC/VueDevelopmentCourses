import {
  PieChartOutlined,
  MailOutlined,
  DesktopOutlined,
  InboxOutlined,
  AppstoreOutlined
} from '@ant-design/icons-vue'
import { VNode } from 'vue'

/**
 * 菜单接口定义
 */
interface MenuItem {
  key: string
  icon?: () => VNode
  label: string
  title: string
  role?: string
}

/**
 * 用户菜单定义
 */
const menus = reactive([
  {
    key: 'user',
    icon: () => h(MailOutlined),
    label: '用户管理',
    title: '用户管理',
    role: 'admin,student'
  },
  {
    key: 'student',
    icon: () => h(AppstoreOutlined),
    label: '学生管理',
    title: '学生管理',
    role: 'admin,student'
  },
  {
    key: 'course',
    icon: () => h(PieChartOutlined),
    label: '课程管理',
    title: '课程管理',
    role: 'admin'
  },
  {
    key: 'course-choosing',
    icon: () => h(DesktopOutlined),
    label: '选课管理',
    title: '选课管理',
    role: 'admin,student'
  },
  {
    key: 'score',
    icon: () => h(InboxOutlined),
    label: '成绩管理',
    title: '成绩管理',
    role: 'admin,student'
  }
])

/**
 * 菜单过滤函数，根据角色的不同对菜单进行过滤
 * @param role 用户角色
 * @returns 返回过滤后的菜单
 */
export function filterMenusByRole(role: string | undefined): MenuItem[] {
  // role为空时，不进行菜单的过滤
  if (!role) {
    return []
  }
  // 根据角色过滤菜单
  const filteredMenus = menus.filter((item: MenuItem) => {
    // 菜单项中role为空或它包含用户的角色，则把该菜单过滤出来
    return !item.role || item.role.includes(role)
  })

  return filteredMenus
}
