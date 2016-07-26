package com.as1124.addressbook.view;

import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import com.as1124.addressbook.AddressbookPlugin;
import com.as1124.addressbook.ImageKeys;
import com.as1124.addressbook.actions.DeleteAddressAction;
import com.as1124.addressbook.view.model.AddressItem;
import com.as1124.addressbook.view.model.AddressManager;

/**
 * 地址本视图
 * 
 * @author as1124(mailto:as1124huang@gmail.com)
 *
 */
public class AddressbookView extends ViewPart {

	public static final String VIEW_ID = "com.as1124.views.AddressbookView";

	private TableViewer viewer;

	private TableColumn nameColumn, categoryColumn;
	
	private Action delAction;
	
	public AddressbookView() {
		
	}

	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

		nameColumn = new TableColumn(viewer.getTable(), SWT.CENTER|SWT.Selection);
		nameColumn.setText("姓名");
		nameColumn.setWidth(100);
		nameColumn.setMoveable(true);
		nameColumn.setResizable(true);
		
		categoryColumn = new TableColumn(viewer.getTable(), SWT.CENTER|SWT.Selection);
		categoryColumn.setText("类别");
		categoryColumn.setWidth(200);
		categoryColumn.setMoveable(false);
		categoryColumn.setResizable(false);
		categoryColumn.addControlListener(new ControlListener() {
			
			public void controlResized(ControlEvent e) {
				
			}
			
			public void controlMoved(ControlEvent e) {
				
			}
		});
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
		
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if(selection==null || selection.isEmpty()){
					delAction.setEnabled(false);
				} else{
					delAction.setEnabled(true);
				}
			}
		});
		viewer.setContentProvider(new AddressviewContentProvider());
		viewer.setLabelProvider(new AddressviewLabelProvider());
		createTableSorter();
		viewer.setInput(AddressManager.getManager());
		
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}
	
	/*
	 * 内容排序
	 */
	private void createTableSorter(){
		// 按照name字段排序
		Comparator<AddressItem> nameComparator = new Comparator<AddressItem>() {

			public int compare(AddressItem o1, AddressItem o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};
		
		// 按照category字段排序
		Comparator<AddressItem> categoryComparator = new Comparator<AddressItem>() {

			public int compare(AddressItem o1, AddressItem o2) {
				return o1.getCategory().compareTo(o2.getCategory());
			}
		};
		
		ViewerSorter sorter = new AddressViewerSorter(new TableColumn[]{nameColumn, categoryColumn},
				new Comparator[]{nameComparator, categoryComparator});
		this.viewer.setSorter(sorter);
	}
	
	private void makeActions(){
		delAction = new DeleteAddressAction(this, "删除", 
				AddressbookPlugin.getImageDescriptor(ImageKeys.IMG_TOOL_DELETE));
		delAction.setDisabledImageDescriptor(AddressbookPlugin.getImageDescriptor(
				ImageKeys.IMG_TOOL_DISABLEDELETE));
	}
	
	/**
	 * 视图右键弹出菜单
	 */
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				AddressbookView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		
		// HUANG 注册到站点进行管理
		getSite().registerContextMenu(menuMgr, viewer);
	}
	
	private void fillContextMenu(IMenuManager manager) {
		// Other plug-ins can contribute there actions here
		//manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		manager.add(delAction);
	}
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(delAction);
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	public AddressItem[] getSelectedAddresses(){
		IStructuredSelection selection = viewer.getStructuredSelection();
		Iterator<?> it = selection.iterator();
		AddressItem[] items = new AddressItem[selection.size()];
		int i=0;
		while(it.hasNext()){
			items[i] = (AddressItem) it.next();
			i++;
		}
		return items;
	}
}
