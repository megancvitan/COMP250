import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException; 


public class CatTree implements Iterable<CatInfo>{
	// root attribute of CatTree 
    public CatNode root;
    
    public CatTree(CatInfo c) {
        this.root = new CatNode(c);
    }
    
    private CatTree(CatNode c) {
        this.root = c;
    }
    
    // creating a root
    public void addCat(CatInfo c)
    {
        this.root = root.addCat(new CatNode(c));
    }
    
    public void removeCat(CatInfo c)
    {
        this.root = root.removeCat(c);
    }
    
    public int mostSenior()
    {
        return root.mostSenior();
    }
    
    public int fluffiest() {
        return root.fluffiest();
    }
    
    public CatInfo fluffiestFromMonth(int month) {
        return root.fluffiestFromMonth(month);
    }
    
    public int hiredFromMonths(int monthMin, int monthMax) {
        return root.hiredFromMonths(monthMin, monthMax);
    }
    
    public int[] costPlanning(int nbMonths) {
        return root.costPlanning(nbMonths);
    }
    
    
    // CatTreeIterator inner class where we implement Iterable
    public Iterator<CatInfo> iterator()
    {
        return new CatTreeIterator();
    }
    
    // contains info about tree structure
    class CatNode {

    	// CatInfo object
        CatInfo data; 
        // all about a cat's seniority
        CatNode senior;
        CatNode same;
        CatNode junior;
        
        // constructor
        public CatNode(CatInfo data) {
            this.data = data;
            this.senior = null;
            this.same = null;
            this.junior = null;
        }
        
        public String toString() {
            String result = this.data.toString() + "\n";
            if (this.senior != null) {
                result += "more senior " + this.data.toString() + " :\n";
                result += this.senior.toString();
            }
            if (this.same != null) {
                result += "same seniority " + this.data.toString() + " :\n";
                result += this.same.toString();
            }
            if (this.junior != null) {
                result += "more junior " + this.data.toString() + " :\n";
                result += this.junior.toString();
            }
            return result;
        }
        
        
        public CatNode addCat(CatNode c) {
        	// addCat is always called on the root node of the tree because of the stuff above
        	// this means that this.c is always the root of the tree

        	// now compare the monthsHired for the catToAdd and the cat at node c
        	// if the catToAdd has higher seniority; looking at monthsHired
        	if (c.data.monthHired < this.data.monthHired) {
        		if (this.senior != null) {
        			// add it again
        			this.senior.addCat(c);
        		} else {
        			// if there is already a cat at that node
        			// gotta compare this cat to the one we wanna add in
        			CatNode catToAdd = new CatNode(c.data);
        			this.senior = catToAdd;
        			return this;
        		}
        		
        	}
        	if (c.data.monthHired > this.data.monthHired) {
        		if (this.junior != null) {
        			this.junior = this.junior.addCat(c);
        		} else {
        		// if the catToAdd has less seniority, it is a junior
        			CatNode catToAdd = new CatNode(c.data);
        			this.junior = catToAdd;
        			return this;
        		}
        	}
        	if (c.data.monthHired == this.data.monthHired) {
        		// if the catToAdd has the same seniority: look at its furThickness
        		if (this.data.furThickness < c.data.furThickness) {
        			// and if it has thicker fur, it has to become the new root
        		    CatInfo s = c.data;
        		    c.data = this.data;
        		    this.data = s;
        			this.addCat(c);
        			//System.out.println("test1");
        			return this;
        		}
        		if (this.same != null) {
        			if (c.data.furThickness <= this.data.furThickness) {
        			// if it has less fur but equal seniority, it goes under same
        				this.same.addCat(c);
        				return this;
        			} else {
        				// if its null, you gotta make the node with the object corresponding to c
        				CatInfo t = c.data;
        				c.data = this.data;
        				this.data = t;
        				this.addCat(c);
        				return this;
        			}
        		} else {
        			CatNode t = new CatNode(c.data);
        			this.same = t;
        			//System.out.println("test2");
        			return this;
        		}		
        	}
        	// return the root of the tree it was called on
        	return this;
        }
        
        
        public CatNode removeCat(CatInfo c) {
            if (this.senior != null && this.senior.data.equals(c) && this.senior.senior == null && this.senior.junior == null && this.senior.same == null) {
            	// if the senior and all the nodes attached are null
            	this.senior = null;
            } else if (this.junior != null && this.junior.data.equals(c) && this.junior.senior == null && this.junior.junior == null && this.junior.same == null) {
            	// if the junior and all the nodes attached are null
            	this.junior = null;
            } else if (this.same != null && this.same.data.equals(c) && this.same.senior == null && this.same.junior == null && this.same.same == null) {
            	// if the same and all the nodes attached are null
            	this.same = null;
            } else if (this.data.equals(c)) {
            	// if the node has the object c corresponding to it, the one we are searching for
            	if (this.same != null) {
            		// if there is a cat at this node, we wanna take its corresponding data             		
            		// its same will now be the sames same
            		this.data = this.same.data;
            		this.same = this.same.same;
            	} else if (this.same == null && this.senior != null) {
            		// if same and its senior are not nodes
            		// make its data the data from senior
            		this.data = this.senior.data;
            		// make nodes that represent all of the surrounding nodes
            		CatNode t_same = this.senior.same;
            		CatNode t_senior = this.senior.senior;
            		CatNode t_junior = this.senior.junior;
            		CatNode j = this.junior;
            		
            		this.senior = null;
            		this.junior = null;
            		
            		// rebuild the tree with the surrounding nodes
            		
            		if (t_same != null) {
            			this.addCat(t_same);
            		}
            		if (t_senior != null) {
            			this.addCat(t_senior);
            		}
            		if (t_junior != null) {
            			this.addCat(t_junior);
            		}
            		if (j != null) {
            			this.addCat(j);
            		}
            	} else if (this.same == null && this.junior != null) {
            		// if this node is null and its junior isnt
            		// make the data come from the juniors object
            		this.data = this.junior.data;
            		// make the temporary nodes to populate the tree
            		CatNode t_same = this.junior.same;
            		CatNode t_senior = this.junior.senior;
            		CatNode t_junior = this.junior.junior;
            		
            		this.junior = null;
            		
            		if (t_same != null) {
            			this.addCat(t_same);
            		}
            		if (t_senior != null) {
            			this.addCat(t_senior);
            		}
            		if (t_junior != null) {
            			this.addCat(t_junior);
            		}	
            	}
            } else {
            	if (this.same != null) {
            		// if theres a cat at the same node, the one we were looking for
            		this.same.removeCat(c);
            	}
            	// also get rid of the seniors and juniors recursively
            	if (this.senior != null) {
            		this.senior.removeCat(c);
            	}
            	if (this.junior != null) {
            		this.junior.removeCat(c);
            	}
            }
            return this; 
        }
        
        public int mostSenior() {
          int month = this.data.monthHired;
          if (this.senior != null) {
        	  // if theres a senior node on this node
        	  month = this.senior.mostSenior();
          }
            return month; 
        }
        
        public int fluffiest() {
        	// guess first 
            int g = this.data.furThickness;
            // go through conditions to see if guess is ok
            // if not, then update g and return it
            if (this.senior != null) {
            	// if theres a senior node on this node
            	int r = this.senior.fluffiest();
            	if (r > g) {
            		g = r;
            	}
            }
            if (this.junior != null) {
            	int r = this.junior.fluffiest();
            	if (r > g) {
            		g = r;
            	}
            }
            if (this.same != null) {
            	int r = this.same.fluffiest();
            	if (r > g) {
            		g = r;
            	}
            }
            return g;
        }
        
        public int hiredFromMonths(int monthMin, int monthMax) {
            if (monthMin > monthMax) {
            	return 0;
            }
            
            int d = 0;
            
            // if the cats month hired is in this range
            if (this.data.monthHired >= monthMin && this.data.monthHired <= monthMax) {
            	// increase counter
            	d++;
            }
            if (this.senior != null) {
            	// run it again
            	d += this.senior.hiredFromMonths(monthMin, monthMax);
            }
            if (this.junior != null) {
            	d += this.junior.hiredFromMonths(monthMin, monthMax);
            }
            if (this.same != null) {
            	d += this.same.hiredFromMonths(monthMin, monthMax);
            }
            return d; 
        }
        
        public CatInfo fluffiestFromMonth(int month) {
        	// looking for cat with thickest fur hired in month month, in the tree with root c
        	// make cat info object
            CatInfo r = null;
        	
            if (this.data.monthHired == month) {
            	// if its month hired is the month we ask for
            	// make a new cat object
            	r = new CatInfo(this.data.name, this.data.monthHired, this.data.furThickness, this.data.nextGroomingAppointment, this.data.expectedGroomingCost);
            	
            	return r;
            
            // finding the node with the right month
            } else {
            	if (this.senior != null) {
            		r = this.senior.fluffiestFromMonth(month);
            		if (r != null) {
            			return r;
            		}
            	}
            	if (this.junior != null) {
            		r = this.junior.fluffiestFromMonth(month);
            		if (r != null) {
            			return r;
            		}
            	}
            	if (this.same != null) {
            		r = this.same.fluffiestFromMonth(month);
            		if (r != null) {
            			return r;
            		}
                }	
            	return r;
            	} 
           } 
                   
        public int[] costPlanning(int nbMonths) {
            int [] array = new int[nbMonths];
            int len = array.length;
            for (int i = 0; i < len; i++) {
            	if (this.data.nextGroomingAppointment == (243+i)) {
            		// collect all of the expected grooming costs from month 243 onwards
            		// gets the cost per month up until month specified
            		array[i] += this.data.expectedGroomingCost;
            	}
            }
            if (this.senior != null) {
            	int [] output = this.senior.costPlanning(nbMonths);
            	for (int i = 0; i < len; i++) {
            		array[i] = array[i]+output[i];
            	}
            }
            if (this.junior != null) {
            	int [] output = this.junior.costPlanning(nbMonths);
            	for (int i = 0; i < len; i++) {
            		array[i] = array[i]+output[i];
            	}
            }
            if (this.same != null) {
            	int [] output = this.same.costPlanning(nbMonths);
            	for (int i = 0; i < len; i++) {
            		array[i] = array[i]+output[i];
            	}
            }
            return array; 
        }
    }
    
    private class CatTreeIterator implements Iterator<CatInfo> {
    	// arraylist where we store the cats
        ArrayList<CatNode> array = new ArrayList<CatNode>();
        CatNode currentNode;
        // index and length
        int i;
        int len = 0;
        
        private void findSeniority(CatNode rootNode) {
        	CatNode currentNode = rootNode;
        	
        	if (rootNode == null) {
        		System.out.println("At null node.");
        	}
        	if (rootNode.senior != null) {
        		findSeniority(rootNode.senior);
        	}
        	if (rootNode.same != null) {
        		findSeniority(rootNode.same);
        	}
        	
        	// we keep this node then
        	array.add(currentNode);
        	// keeps track of length of arraylist
        	len++;
        	
        	if (rootNode.junior != null) {
        		findSeniority(rootNode.junior);
        	}
        }
        
        public CatTreeIterator() {
        	findSeniority(root);
        	i = 0;
        	if (!array.isEmpty()) {
        		// if our array has a next element
        		if (array.get(i).data != null) {
        			// save the value if it isnt null
        			currentNode = array.get(i);
        		}
        	}
        }
        
        public CatInfo next(){
            // gets the next most senior cat in the tree
        	CatInfo temp = null;
        	i++;
        	
        	if (i < len) {
        		temp = currentNode.data;
        		currentNode = array.get(i);
        	} else if (i > len) {
        		if (currentNode != null) {
        			temp = currentNode.data;
        		}
        		currentNode = null;
        	} else {
        		throw new NoSuchElementException();
        	}
        	return temp;
        }
        
        public boolean hasNext() {
            // returns boolean on if there is a next or not
        	boolean next = (currentNode != null);
            return next;
            		
        }
    } 
}

