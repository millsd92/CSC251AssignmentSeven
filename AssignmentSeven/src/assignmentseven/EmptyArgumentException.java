package assignmentseven;

/******************************************************************************
 *                          EmptyArgumentException.java                       *
 * Author:      David Mills                                                   *
 * Version:     1.0.0                                                         *
 * Date:        Mar 5, 2019                                                   *
 *                                                                            *
 * Description:                                                               *
 *      All this class is here for is to reiterate previously learned lessons *
 *      about creating your own exceptions. I just wanted to do it for fun.   *
 *                                                                            *
 * Version History:                                                           *
 *           1.0.0:                                                           *
 *      Started the class. It is a mirror class for IllegalArgumentException. *
 *                                                                            *
 * @author David Mills                                                        *
 * @version 1.0.0                                                             *
 ******************************************************************************/
public class EmptyArgumentException extends IllegalArgumentException
{
    /**
     * Simply calls super's empty constructor.
     */
    public EmptyArgumentException() { super(); }
    
    /**
     * Simply calls the super's constructor.
     * @param message A message that details the specific exception.
     */
    public EmptyArgumentException(String message) { super(message); }
    
    /**
     * Simply calls the super's constructor.
     * @param message A message that details the specific exception.
     * @param cause The cause of the exception.
     */
    public EmptyArgumentException(String message, Throwable cause)
    { super(message, cause); }

    
    /**
     * Simply calls the super's constructor.
     * @param cause The cause of the exception.
     */
    public EmptyArgumentException(Throwable cause) { super(cause); }
}
